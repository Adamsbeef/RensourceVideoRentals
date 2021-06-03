package com.example.rensourcevideorentals.repositories;

import com.example.rensourcevideorentals.entities.RentRequests;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRequestRepository extends BaseRepository<RentRequests, Long> {
}
