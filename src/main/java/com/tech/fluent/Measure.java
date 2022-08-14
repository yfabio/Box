package com.tech.fluent;

import org.springframework.stereotype.Service;

@Service
public interface Measure {

	Measure scale(String value,By which);
	Measure reload(MeasureResult result);
}
