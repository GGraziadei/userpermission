package it.goodgamegroup.up.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Table(name = "verification_token")
public class VerificationToken {

    public static final int EXPIRATION = 60 * 24;

    public VerificationToken(UserAuthentication userAuthentication) {
        this.token = UUID.randomUUID().toString();
        this.userAuthentication = userAuthentication;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @JsonIgnore
    private Long id;

    @Column(name = "TOKEN")
    private String token;

    @ManyToOne(targetEntity = UserAuthentication.class, fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "AUTH_ID")
    @JsonIgnore
    private UserAuthentication userAuthentication;

    @Column(name = "EXPIRY_DATE")
    private Date expiryDate;

    public static Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

}
