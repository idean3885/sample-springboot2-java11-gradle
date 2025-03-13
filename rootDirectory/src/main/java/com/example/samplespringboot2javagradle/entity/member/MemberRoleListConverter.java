package com.example.samplespringboot2javagradle.entity.member;

import com.example.samplespringboot2javagradle.consts.entity.MemberRole;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 *
 *
 * <h3>Member role converter</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Converter
public class MemberRoleListConverter implements AttributeConverter<List<MemberRole>, String> {

  @Override
  public String convertToDatabaseColumn(List<MemberRole> attribute) {
    return StringUtils.join(attribute, ",");
  }

  @Override
  public List<MemberRole> convertToEntityAttribute(String dbData) {
    return Arrays.stream(dbData.split(",")).map(MemberRole::valueOf).collect(Collectors.toList());
  }
}
