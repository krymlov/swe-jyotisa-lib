/*
 * Copyright (C) By the Author
 * Author    Yura Krymlov
 * Created   2021-01
 */

package org.jyotisa.upagraha;

import org.jyotisa.api.upagraha.IUpagrahaParivesha;

/**
 * 3. Parivesha
 *
 * @author Yura Krymlov
 * @version 1.0, 2021-01
 */
public enum UpagrahaParivesha implements IUpagrahaParivesha {
    UG3,
    PAR;

    @Override
    public IUpagrahaParivesha[] all() {
        return values();
    }
}
