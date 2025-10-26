// Em: shared.application.EventDispatcher.java
package org.seniorcare.shared.application;

import java.util.List;

public interface EventDispatcher {

    void dispatch(List<Object> events);


    void dispatch(Object event);
}