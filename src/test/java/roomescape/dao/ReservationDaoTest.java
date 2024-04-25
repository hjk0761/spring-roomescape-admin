package roomescape.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationDaoTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final ReservationDao reservationDao;

    @Autowired
    public ReservationDaoTest(JdbcTemplate jdbcTemplate, ReservationDao reservationDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationDao = reservationDao;
    }

    @Test
    void findAllTest() {
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    void findById() {
        Reservation reservation = reservationDao.findById(1L).get();

        assertThat(reservation.getId()).isEqualTo(1L);
    }

    @Test
    void insertTest() {
        Long index = jdbcTemplate.queryForObject("SELECT count(*) FROM reservation", Long.class);
        Long id = reservationDao.insert("토미", "2024-01-02", 1L).get();

        assertThat(id).isEqualTo(index + 1);
    }

    @Test
    void deleteByIdTest() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO reservation(name, date, time_Id) VALUES (?, ?, ?)",
                    new String[]{"id"});
            ps.setString(1, "네오");
            ps.setString(2, "2024-01-03");
            ps.setLong(3, 1L);
            return ps;
        }, keyHolder);

        Long key = keyHolder.getKey().longValue();
        reservationDao.deleteById(key);

        assertThat(reservationDao.findById(key)).isEqualTo(Optional.empty());
    }
}
