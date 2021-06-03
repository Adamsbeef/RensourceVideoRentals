package com.example.rensourcevideorentals.repositories;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<M,ID extends Serializable> extends PagingAndSortingRepository<M,ID> {

}
