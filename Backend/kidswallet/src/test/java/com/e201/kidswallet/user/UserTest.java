 package com.e201.kidswallet.user;

 import com.e201.kidswallet.AbstractTest;
 import com.e201.kidswallet.common.exception.StatusCode;
 import com.e201.kidswallet.user.dto.RegisterRequestDTO;
 import com.e201.kidswallet.user.dto.RelationRequestDTO;
 import com.e201.kidswallet.user.dto.UserLoginDTO;
 import com.e201.kidswallet.user.enums.Gender;
 import com.e201.kidswallet.user.enums.Role;
 import com.e201.kidswallet.user.service.UserService;
 import org.junit.jupiter.api.*;
 import org.springframework.beans.factory.annotation.Autowired;

 import java.time.LocalDate;
 import java.time.format.DateTimeFormatter;

 import static org.junit.jupiter.api.Assertions.assertEquals;

 @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 public class UserTest extends AbstractTest {

     @Autowired
     UserService userService;

     private RegisterRequestDTO parentsRegisterRequestDTO;
     private RegisterRequestDTO childsRegisterRequestDTO;
     private UserLoginDTO parentLoginDTO;
     private UserLoginDTO childLoginDto;

     @BeforeEach
     public void setpUp() {
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
         LocalDate PrentBirthDate = LocalDate.parse("1999-01-29", formatter);
         LocalDate ChildbirthDate = LocalDate.parse("2018-01-29", formatter);

         parentsRegisterRequestDTO = new RegisterRequestDTO("오성혁", "1234", PrentBirthDate
                                                             , Gender.MALE
                                                             ,"오성혁", Role.PARENT);

         childsRegisterRequestDTO = new RegisterRequestDTO("오성혁주니어", "1234", ChildbirthDate
                                                             , Gender.MALE
                                                             ,"오성혁주니어", Role.CHILD);
         parentLoginDTO = new UserLoginDTO(parentsRegisterRequestDTO.getUserName(),parentsRegisterRequestDTO.getUserPassword());
         childLoginDto = new UserLoginDTO(childsRegisterRequestDTO.getUserName(),childsRegisterRequestDTO.getUserPassword());
     }

     @Test
     @Order(1)
     public void register() {

         if(StatusCode.NO_USER==userService.loginUser(parentLoginDTO).getStatusCode()){
             assertEquals(StatusCode.SUCCESS,userService.registerUser(parentsRegisterRequestDTO));
         }
         if(StatusCode.NO_USER==userService.loginUser(childLoginDto).getStatusCode()){
             assertEquals(StatusCode.SUCCESS,userService.registerUser(childsRegisterRequestDTO));
         }
     }

     @Test
     @Order(2)
     public void login() {
         assertEquals(StatusCode.SUCCESS,userService.loginUser(parentLoginDTO).getStatusCode());
         assertEquals(StatusCode.SUCCESS,userService.loginUser(childLoginDto).getStatusCode());

     }

     @Test
     @Order(3)
     public void setRelation() {
         RelationRequestDTO requestDTO = new RelationRequestDTO(childLoginDto.getUserName(),parentLoginDTO.getUserName());
         assertEquals(StatusCode.SUCCESS,userService.setRelation(requestDTO));
     }

 }
