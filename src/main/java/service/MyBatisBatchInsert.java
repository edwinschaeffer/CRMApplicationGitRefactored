package service;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mapper.PotentialLeadMapper;
import vo.PotentialLead;

@Service
public class MyBatisBatchInsert implements BatchInsert {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

	@Override
	@Transactional
	public void insertBatch(List<PotentialLead> plList) {
        try (SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH)) {
        	PotentialLeadMapper mapper = sqlSession.getMapper(PotentialLeadMapper.class);
            for (PotentialLead pl : plList) {
                mapper.insertPL(pl);
            }
            sqlSession.commit();
        }
	}
}