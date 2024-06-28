package com.example.samplespringboot2javagradle.consts.prometheus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 *
 * <h3>Step unit</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@RequiredArgsConstructor
public enum StepUnit {
  SECOND("s"),
  MINUTE("m"),
  HOUR("h"),
  DAY("d");

  private final String unit;
}
