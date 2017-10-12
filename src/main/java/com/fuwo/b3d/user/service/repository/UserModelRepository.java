package com.fuwo.b3d.user.service.repository;

import com.fuwo.b3d.enums.StatusEnum;
import com.fuwo.b3d.model.model.Model;
import com.fuwo.b3d.user.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface UserModelRepository extends JpaRepository<UserModel, Long> {

    List<UserModel> findByUidAndModelIdAndStatus(Integer uid, Integer modelId, StatusEnum statusEnum);

    Integer countByModelId(Integer modelId);

    List<UserModel> findByUidAndAndStatus(Integer uid, StatusEnum status);

}
