package de.weichert.multitenancy;

import de.weichert.multitenancy.configuration.MultitenancyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Jannik on 30.06.15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MultitenancyConfig.class)
public @interface EnableMultiTenancy {
}
