package com.bis.sourcelead.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author RajaGopal Reddy
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD})
public @interface Required {
    public String desc();
    public boolean alphaNumericOnly() default false;
    
    public String dependsOn() default "";
    public String[] parentValue() default "";
    public boolean onlyDependsContainsValue() default true;
    public boolean timesheetListValidate() default true;
    public boolean timesheetHours() default true;
}
