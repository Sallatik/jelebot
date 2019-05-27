/**
 * Copy-pasted from: https://github.com/snqlby/tgwebhook/blob/master/src/main/java/com/github/snqlby/tgwebhook/utils/AnnotationUtils.java
 * Author: https://github.com/snqlby
 */

package sallat.jelebot;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

abstract class AnnotationUtils {

    /**
     * Extract the annotation from current class or subclasses.
     *
     * It can be used to extract the annotations from proxied methods
     * @return annotation, otherwise null
     */
    public static <A extends Annotation> A getMethodAnnotation(Class<A> annotationClass,
                                                               Class<?> clazz, Method method) {
        return findAnnotation(annotationClass, clazz, method);
    }

    private static <A extends Annotation> A findAnnotation(Class<A> annotationClass,
                                                           Class<?> searchClass, Method parentMethod) {
        try {
            final Method method = searchClass
                    .getDeclaredMethod(parentMethod.getName(), parentMethod.getParameterTypes());
            final A annotation = method.getAnnotation(annotationClass);
            if (annotation != null) {
                return annotation;
            }

            final Class<?> superclass = method.getDeclaringClass().getSuperclass();
            if (superclass != null) {
                return getMethodAnnotation(annotationClass, superclass, method);
            }
        } catch (NoSuchMethodException ignored) {
            // Ignore the following exception and return null
        }
        return null;
    }
}
