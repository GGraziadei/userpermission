package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.goodgamegroup.up.utilities.FiscalCodePattern;
import it.goodgamegroup.up.utilities.PhoneNumberPattern;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Reference;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
@Table(name = "up_user", indexes = {
        @Index(name = "FISCAL_CODE", columnList = "FISCAL_CODE", unique = true),
        @Index(name = "EMAIL", columnList = "EMAIL", unique = true)
})
public class User {
    @Id
    @Column(name = "USER_ID", nullable = false, length = 16)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull
    @Size(min = 3 , message = "firstName min 3 characters")
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @NotNull
    @Size(min = 3 , message = "lastName min 3 characters")
    private String lastName;

    @Column(name = "FISCAL_CODE", nullable = false)
    @Pattern(regexp = FiscalCodePattern.pattern, message = "insert a valid fiscal code")
    private String fiscalCode;

    @Column(name = "EMAIL", nullable = false)
    @NotNull
    @Size(min = 3 )
    @Email(message = "insert a valid email")
    private String email;

    @Column(name = "PHONE_NUMBER", nullable = false)
    @Pattern(regexp = PhoneNumberPattern.pattern,message = "insert a valid phone number")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    @Builder.Default
    @JsonIgnore
    @ToString.Exclude
    private Set<Permission> permissions = new LinkedHashSet<>();

    @OneToOne(fetch = FetchType.LAZY,  mappedBy = "user" , cascade = CascadeType.ALL , optional = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "USER_ID" , referencedColumnName = "USER_ID")
    @ToString.Exclude
    private UserAuthentication userAuthentication;

}