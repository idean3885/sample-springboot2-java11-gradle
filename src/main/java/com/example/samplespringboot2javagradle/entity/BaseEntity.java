package com.example.samplespringboot2javagradle.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *
 *
 * <h3>Base Entity</h3>
 *
 * @author dongyoung.kim
 * @since 1.0
 */
@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity {

  @Column(updatable = false, nullable = false)
  @CreatedBy
  private Long createdBy;

  @Column(updatable = false, nullable = false)
  @CreatedDate
  private LocalDateTime createdDate;

  @Column(nullable = false)
  @LastModifiedBy
  private Long lastModifiedBy;

  @Column(nullable = false)
  @LastModifiedDate
  private LocalDateTime lastModifiedDate;
}
