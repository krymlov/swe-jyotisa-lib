/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2026-08
 */
package org.jyotisa.refbase;

import org.swisseph.api.ISweEnumSequence;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * Walks an {@link ISweEnumSequence} the way callers navigate one - {@code first()}, {@code last()},
 * {@code following()}, {@code previous()} and {@code follow(k)} - and renders every element it
 * lands on as
 *
 * <pre>
 * following[3] | KARKATA | R4 | 4 | 4 | 4 | 30.00000000
 *      step        name    code fid uid ord   length
 * </pre>
 *
 * The five columns after the step are what was asked for; the enum constant's own {@code name()}
 * is there because without it a leaf's block is unreadable and half-blind. {@code GrahaGuru}
 * declares five constants for one graha - {@code G3, GU, GURU, Ju, Jupiter} - and all five share
 * a code, a fid and a uid, so the name is the only column that says which alias a step landed on,
 * and the only one that would notice an alias being renamed.
 *
 * <h2>Why a golden file rather than assertions alone</h2>
 * {@code follow()} is one default method on {@code ISweEnumSequence}, inherited by every registry
 * and every leaf in two projects, and it has been wrong before: until 2026-08-14 it jumped
 * straight to {@code last()} on any negative step and reduced positive ones modulo {@code last()}
 * instead of the element count, returning a <b>silently wrong neighbour</b> rather than failing.
 * Invariants catch the classes of error you thought of; a file that records where all
 * ~250 sequences actually land catches the ones you did not.
 *
 * <h2>Two tiers, both covered</h2>
 * A registry ({@code ERasi}) and each of its leaves ({@code RasiMesha}) are <i>both</i>
 * sequences, and they navigate differently: the registry skips its {@code NIL} member because it
 * overrides {@code first()}/{@code last()}, while a leaf cycles through the aliases one member
 * declares for itself ({@code GrahaGuru} has five). That leaf cycle is not academic - it is what
 * {@code Kundali.toString()} prints, through {@code following()}.
 *
 * @author Yura Krymlov
 * @version 1.0, 2026-08
 * @see ISweEnumSequence#follow(int)
 */
public final class SequenceReference {

    /** the folder these files live in, under {@link ReferenceFile#RESOURCES} */
    public static final String FOLDER = "sequence/";

    private SequenceReference() {
    }

    // ------------------------------------------------------------------ discovery

    /**
     * Every enum on the classpath under {@code rootPackage} that is an {@link ISweEnumSequence},
     * sorted by class name.
     * <p>
     * Discovered rather than listed, so a family added later is covered without anyone
     * remembering to add it - and {@code SequenceReferenceTest} notices the new class as a
     * missing reference file rather than not noticing at all.
     * <p>
     * Enum constants that carry a body compile to synthetic subclasses ({@code ERasi$1}); those
     * are filtered out by {@link Class#isEnum()}, which is false for them.
     */
    public static List<Class<?>> discover(final Class<?> anchor, final String rootPackage) {
        final File root = codeSourceOf(anchor);
        final List<Class<?>> found = new ArrayList<>();

        // a project's own classes are a directory, a dependency is a jar - both have to be read,
        // because the Nil* sequences live in swe-jyotisa-api and would be missed otherwise
        if (root.isDirectory()) {
            collectClasses(new File(root, rootPackage.replace('.', '/')), root, found);
        } else {
            collectFromJar(root, rootPackage, found);
        }

        found.sort(Comparator.comparing(Class::getName));
        return found;
    }

    private static void collectFromJar(final File jar, final String rootPackage,
                                       final List<Class<?>> found) {
        final String prefix = rootPackage.replace('.', '/') + '/';

        try (java.util.jar.JarFile file = new java.util.jar.JarFile(jar)) {
            final java.util.Enumeration<java.util.jar.JarEntry> entries = file.entries();

            while (entries.hasMoreElements()) {
                final String path = entries.nextElement().getName();
                if (!path.startsWith(prefix) || !path.endsWith(".class")) continue;

                consider(path.substring(0, path.length() - ".class".length())
                        .replace('/', '.'), found);
            }
        } catch (IOException unreadable) {
            throw new IllegalStateException("cannot read " + jar, unreadable);
        }
    }

    private static File codeSourceOf(final Class<?> anchor) {
        try {
            return new File(anchor.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            throw new IllegalStateException("cannot locate the compiled classes of " + anchor, e);
        }
    }

    private static void collectClasses(final File dir, final File root, final List<Class<?>> found) {
        final File[] entries = dir.listFiles();
        if (null == entries) return;

        for (File entry : entries) {
            if (entry.isDirectory()) {
                collectClasses(entry, root, found);
                continue;
            }
            if (!entry.getName().endsWith(".class")) continue;

            final String path = root.toURI().relativize(entry.toURI()).getPath();
            consider(path.substring(0, path.length() - ".class".length()).replace('/', '.'), found);
        }
    }

    private static void consider(final String name, final List<Class<?>> found) {
        final Class<?> type;
        try {
            type = Class.forName(name, false, SequenceReference.class.getClassLoader());
        } catch (Throwable notLoadable) {
            return; // nothing here should fail, but a broken class must not stop discovery
        }

        if (type.isEnum() && ISweEnumSequence.class.isAssignableFrom(type)) found.add(type);
    }

    // ------------------------------------------------------------------ walking

    /**
     * The four walks of one sequence, preceded by {@code first()} and {@code last()}.
     * <p>
     * {@code follow(+k)} and {@code follow(-k)} for every {@code k} below the element count are
     * what make this an exhaustive check of the multi-step navigation rather than a sample: both
     * halves of the range that the old implementation got wrong are exercised for every sequence
     * in the workspace.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static List<String> walk(final Class<?> type) {
        final ISweEnumSequence[] constants = (ISweEnumSequence[]) type.getEnumConstants();

        final List<String> lines = new ArrayList<>();
        lines.add("== " + type.getName() + " | " + constants.length + " constants");
        lines.add("-- step | name | code | fid | uid | ordinal | length");

        if (0 == constants.length) {
            lines.add("(no constants)");
            return lines;
        }

        final ISweEnumSequence first = constants[0].first();
        final ISweEnumSequence last = constants[0].last();
        final int count = last.ordinal() - first.ordinal() + 1;

        lines.add(render("first", first));
        lines.add(render("last", last));

        ISweEnumSequence cursor = first;
        for (int i = 0; i < count; i++) {
            lines.add(render("following[" + i + "]", cursor));
            cursor = cursor.following();
        }

        cursor = last;
        for (int i = 0; i < count; i++) {
            lines.add(render("previous[" + i + "]", cursor));
            cursor = cursor.previous();
        }

        for (int k = 0; k < count; k++) lines.add(render("follow(+" + k + ")", first.follow(k)));
        for (int k = 0; k < count; k++) lines.add(render("follow(-" + k + ")", first.follow(-k)));

        return lines;
    }

    /**
     * One element.
     * <p>
     * Every column is read defensively: a family whose members are declared but not implemented
     * ({@code EMaasa}) throws from the accessor its {@code code()} or {@code length()} delegates
     * to, and recording that as {@code !NotImplementedException} is more useful than letting the
     * whole file fail to build. {@code Locale.ROOT} keeps the decimal point a point - the default
     * locale here renders a comma.
     */
    private static String render(final String step, final ISweEnumSequence<?> element) {
        return step
                + " | " + element.name()
                + " | " + safe(element::code)
                + " | " + safe(() -> String.valueOf(element.fid()))
                + " | " + safe(() -> String.valueOf(element.uid()))
                + " | " + element.ordinal()
                + " | " + safe(() -> lengthOf(element));
    }

    /** {@code length()} lives on the jyotisa side only - {@code ISweEnumSequence} has no notion */
    private static String lengthOf(final ISweEnumSequence<?> element) {
        if (!(element instanceof org.jyotisa.api.IKundaliSegment)) return "-";
        return String.format(Locale.ROOT, "%.8f",
                ((org.jyotisa.api.IKundaliSegment) element).length());
    }

    private static String safe(final java.util.function.Supplier<String> column) {
        try {
            final String value = column.get();
            return null == value ? "null" : value;
        } catch (Throwable refused) {
            return "!" + refused.getClass().getSimpleName();
        }
    }

    // ------------------------------------------------------------------ checking

    public static void assertMatchesReference(final String name, final List<String> lines)
            throws IOException {
        ReferenceFile.assertMatches(FOLDER + name, lines);
    }
}
