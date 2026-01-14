package com.example.loginproject.domain.weather;

import com.example.loginproject.domain.location.Location;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "weather")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Weather {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long weatherId; // 날씨 정보 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private Location location; // 위치 정보 (FK)

    @Column(name = "temperature", nullable = false)
    private Double temperature; // 현재 기온 (°C)

    @Column(name = "feels_like", nullable = false)
    private Double feelsLike; // 체감 온도 (°C)

    @Column(name = "humidity", nullable = false)
    private Integer humidity; // 습도 (%)

    @Column(name = "wind_speed", nullable = false)
    private Double windSpeed; // 풍속 (m/s)

    @Column(name = "weather_main", nullable = false, length = 50)
    private String weatherMain; // 날씨 상태 (Clear, Clouds, Rain 등)

    @Column(name = "weather_description", length = 100)
    private String weatherDescription; // 날씨 상세 설명 (맑음, 흐림 등)

    @Column(name = "weather_icon", length = 10)
    private String weatherIcon; // 날씨 아이콘 코드 (01d, 02n 등)

    @CreationTimestamp
    @Column(name = "recorded_at", updatable = false)
    private LocalDateTime recordedAt; // 기록 시간

    @Builder
    public Weather(Location location, Double temperature, Double feelsLike,
                   Integer humidity, Double windSpeed, String weatherMain,
                   String weatherDescription, String weatherIcon) {
        this.location = location;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.weatherMain = weatherMain;
        this.weatherDescription = weatherDescription;
        this.weatherIcon = weatherIcon;
    }
}