package marcelodev.comu_carona.models

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import marcelodev.comu_carona.models.Permission

@Entity
@Table(name = "users")
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(name = "user_id", unique = true)
    private var userId: String? = null

    @Column(name = "username")
    private var username: String? = null

    @Column(name = "birth_date")
    private var birthDate: String? = null

    @Column(name = "phone_number")
    private var phoneNumber: String? = null

    @Column(name = "photo_url")
    private var photoUrl: String? = null

    @Column(name = "password")
    private var password: String? = null

    @Column(name = "account_non_expired")
    var accountNonExpired: Boolean? = null

    @Column(name = "account_non_locked")
    var accountNonLocked: Boolean? = null

    @Column(name = "credentials_non_expired")
    var credentialsNonExpired: Boolean? = null

    @Column(name = "enabled")
    var enabled: Boolean? = null

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_permission",
        joinColumns = [JoinColumn(name = "id_user")],
        inverseJoinColumns = [JoinColumn(name = "id_permission")]
    )
    var permissions: MutableList<Permission>? = null


    val roles: List<String?>
        get() {
            val roles: MutableList<String?> = ArrayList()
            for (permission in permissions!!) {
                roles.add(permission.description)
            }
            return roles
        }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return permissions!!
    }

    override fun getPassword(): String {
        return password!!
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getBirthDate(): String {
        return birthDate!!
    }

    fun setBirthDate(birthDate: String) {
        this.birthDate = birthDate
    }

    fun getPhoneNumber(): String {
        return phoneNumber!!
    }

    fun setPhoneNumber(phoneNumber: String) {
        this.phoneNumber = phoneNumber
    }

    fun setPhotoUrl(photoUrl: String) {
        this.photoUrl = photoUrl
    }

    fun getPhotoUrl(): String {
        return photoUrl!!
    }

    fun getUserId(): String {
        return userId!!
    }

    fun setUserId(userId: String) {
        this.userId = userId
    }

    override fun getUsername(): String {
        return username!!
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun isAccountNonExpired(): Boolean {
        return accountNonExpired!!
    }

    override fun isAccountNonLocked(): Boolean {
        return accountNonLocked!!
    }

    override fun isCredentialsNonExpired(): Boolean {
        return credentialsNonExpired!!
    }

    override fun isEnabled(): Boolean {
        return enabled!!
    }
}