package com.chilo_tech.demo.common.utility;

import java.time.Instant;

public class IdentifiantUnique {

    public static Long setIdentifiantBytime(){
        return Instant.now().toEpochMilli();
    }

}
