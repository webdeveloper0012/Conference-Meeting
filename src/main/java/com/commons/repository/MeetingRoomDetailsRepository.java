package com.commons.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.commons.entity.MeetingRoomDetails;

@Repository
public interface MeetingRoomDetailsRepository extends JpaRepository<MeetingRoomDetails, Long>, JpaSpecificationExecutor<MeetingRoomDetails>{

	List<MeetingRoomDetails> findByLocationOrderByNameDesc(String location);
 
}
