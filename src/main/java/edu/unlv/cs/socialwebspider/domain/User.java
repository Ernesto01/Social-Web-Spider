package edu.unlv.cs.socialwebspider.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Date;

import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;
import edu.unlv.cs.socialwebspider.domain.Profile;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUsersByEmailAddress", "findUsersByActivationKeyAndEmailAddress", "findUsersByUsername" })
public class User {

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String username;	// Stores the user's username

    @NotNull
    @Size(min = 1)
    private String password;	// Stores the users's encrypted password

    @NotNull
    @Column(unique = true)
    @Size(min = 1)
    private String emailAddress;	// Stores the user's email address

    private Boolean enabled;		// Stores the enabled flag for the user

    private String activationKey;	// Stores the activation key for the user

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "M-")
    private Date activationDate;	// Stores the activation date of the user

    @OneToOne
    private Profile profile;		// Stores the user's customizable profile

    private Boolean admin;			// Stores the admin flag for the user
    
    public long getUserIdByUsername()
    {
    	TypedQuery<User> usernameQuery = User.findUsersByUsername(this.username);	// Sets up a query to check for existing username
    	
    	// If results is 0 user is either an admin or profile doesn't exist
    	if(usernameQuery.getResultList().isEmpty())
    		return -1L;
    	
    	// Return the user's ID
    	User user = usernameQuery.getSingleResult();
    	return user.getId();
    }
}
