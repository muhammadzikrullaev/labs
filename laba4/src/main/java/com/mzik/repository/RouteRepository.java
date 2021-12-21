package com.mzik.repository;

import com.mzik.model.Route;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RouteRepository implements RepositoryDao<Route> {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public Collection<Route> findAll() {
        String sql = "SELECT id, route_no, departure_date, departure_car_park, arrival_date, arrival_car_park, bus_id, status FROM route ";
        return jdbc.query(sql, new RouteRowMapper());
    }

    @Override
    public Optional<Route> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Route object) {

    }

    @Override
    public void update(Route object) {

    }

    @Override
    public Collection<Route> findByName(String name) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }

    public List<Integer> getIds() {
        String sql = "SELECT id FROM route";
        return jdbc.query(sql, (ResultSetExtractor<List<Integer>>) rs -> {
            var list = new ArrayList<Integer>();
            while (rs.next()){
                list.add(rs.getInt("id"));
            }
            return list;
        });
    }
}
