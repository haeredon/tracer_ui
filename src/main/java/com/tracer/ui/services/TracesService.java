package com.tracer.ui.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Service;
import com.tracer.gen.model.Trace;

@Service
public class TracesService {
    
    JdbcTemplate jdbcTemplate;

    PreparedStatementCreator getTracesCreator;
    ResultSetExtractor<List<Trace>> tracesExtractor;


    TracesService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        getTracesCreator = new PreparedStatementCreator() {
            String query = "SELECT * FROM traces LIMIT ? OFFSET ?";

            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                return connection.prepareStatement(query);
            }
        };

        tracesExtractor  = new ResultSetExtractor<List<Trace>>() {
            
            public List<Trace> extractData(ResultSet resultSet) throws SQLException {
                
                List<Trace> traces = new ArrayList<Trace>();

                while(resultSet.next()) {                    
                    traces.add(new Trace().something(2));
                }   
                
                return traces;
            }
        };

    }
    
    private static class GetTracesSetter implements PreparedStatementSetter{

        private int limit;
        private int offset;
        
        public GetTracesSetter(int limit, int offset) {
            this.limit = limit;
            this.offset = offset;
        }
        @Override
        public void setValues(PreparedStatement preparedStatement) throws SQLException {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
        }
    }

    public List<Trace> getTraces(Integer limit, Integer offset) {
        return jdbcTemplate.query(getTracesCreator, new GetTracesSetter(10, 0), tracesExtractor);
    }    
}
