package com.example.loginproject.domain.location;

import com.example.loginproject.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name", nullable = false)
    private String locationName;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "is_pinned", nullable = false)
    private Boolean isPinned = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_no", nullable = false)
    private User user;

    @Builder
    public Location(String locationName, Double latitude, Double longitude,
                    Boolean isPinned, User user) {
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isPinned = isPinned != null ? isPinned : false;
        this.user = user;
    }

    public void updatePinStatus(Boolean isPinned) {
        this.isPinned = isPinned;
    }
}