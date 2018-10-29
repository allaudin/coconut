package com.mallaudin.demo;

import com.mallaudin.coconut.Coconut;
import com.mallaudin.coconut.validation.CoconutValidationProvider;

public class Validatons extends CoconutValidationProvider {


    public void check() {
        Coconut.Companion.init(new CoconutValidationProvider());
    }
}
