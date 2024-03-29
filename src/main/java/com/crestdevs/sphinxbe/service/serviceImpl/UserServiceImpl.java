package com.crestdevs.sphinxbe.service.serviceImpl;

import com.crestdevs.sphinxbe.config.AppConstants;
import com.crestdevs.sphinxbe.entity.*;
import com.crestdevs.sphinxbe.exception.ResourceNotFoundException;
import com.crestdevs.sphinxbe.payload.AlumniDto;
import com.crestdevs.sphinxbe.payload.StudentDto;
import com.crestdevs.sphinxbe.payload.TeacherDto;
import com.crestdevs.sphinxbe.payload.UserDto;
import com.crestdevs.sphinxbe.repository.*;
import com.crestdevs.sphinxbe.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private StudentRepo studentRepo;

    @Autowired
    private AlumniRepo alumniRepo;

    @Autowired
    private TeacherRepo teacherRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        //date to set registration date of user
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = dateTimeFormatter.format(now);

        String type = userDto.getType();

        User user = this.modelMapper.map(userDto, User.class);

        Optional<User> checkUser = this.userRepo.findByEmail(userDto.getEmail());
        if (checkUser.isPresent()) {
            return null;
        }

        user.setRegistrationDate(date);
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);


        UserDto newUserDto = this.modelMapper.map(newUser, UserDto.class);

        if (type.equalsIgnoreCase("student")) {
            createStudent(newUserDto);
        } else if (type.equalsIgnoreCase("alumni")) {
            createAlumni(newUserDto);
        } else {
            createTeacher(newUserDto);
        }

        return this.modelMapper.map(newUser, UserDto.class);

    }

    private void createStudent(UserDto userDto) {

        StudentDto studentDto = new StudentDto();

        studentDto.setStudentId(userDto.getUserId());
        studentDto.setFirstName(userDto.getFirstName());
        studentDto.setLastName(userDto.getLastName());
        studentDto.setEmail(userDto.getEmail());
        studentDto.setGender(userDto.getGender());
        studentDto.setCollege(userDto.getCollege());
        studentDto.setPassword(userDto.getPassword());
        studentDto.setUserName(userDto.getUsername());
        studentDto.setRegistrationDate(userDto.getRegistrationDate());
        studentDto.setUser(userDto);

        Student student = this.modelMapper.map(studentDto, Student.class);

        this.studentRepo.save(student);

        System.out.println("Student created!" + student);
    }

    private void createAlumni(UserDto userDto) {

        AlumniDto alumniDto = new AlumniDto();

        alumniDto.setAlumniId(userDto.getUserId());
        alumniDto.setFirstName(userDto.getFirstName());
        alumniDto.setLastName(userDto.getLastName());
        alumniDto.setEmail(userDto.getEmail());
        alumniDto.setGender(userDto.getGender());
        alumniDto.setCollege(userDto.getCollege());
        alumniDto.setPassword(userDto.getPassword());
        alumniDto.setUserName(userDto.getUsername());
        alumniDto.setRegistrationDate(userDto.getRegistrationDate());
        alumniDto.setUser(userDto);


        Alumni alumni = this.modelMapper.map(alumniDto, Alumni.class);

        this.alumniRepo.save(alumni);

        System.out.println("Alumni created!" + alumni);
    }

    private void createTeacher(UserDto userDto) {

        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setTeacherId(userDto.getUserId());
        teacherDto.setFirstName(userDto.getFirstName());
        teacherDto.setLastName(userDto.getLastName());
        teacherDto.setEmail(userDto.getEmail());
        teacherDto.setGender(userDto.getGender());
        teacherDto.setCollege(userDto.getCollege());
        teacherDto.setPassword(userDto.getPassword());
        teacherDto.setUserName(userDto.getUsername());
        teacherDto.setRegistrationDate(userDto.getRegistrationDate());
        teacherDto.setUser(userDto);


        Teacher teacher = this.modelMapper.map(teacherDto, Teacher.class);

        this.teacherRepo.save(teacher);

        System.out.println("Teacher created!!" + teacher);

    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User fetchedUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user_id", userId));

        fetchedUser.setFirstName(userDto.getFirstName());
        fetchedUser.setLastName(userDto.getLastName());
        fetchedUser.setGender(userDto.getGender());
        fetchedUser.setCollege(userDto.getCollege());
        fetchedUser.setEmail(userDto.getEmail());

        User savedUser = this.userRepo.save(fetchedUser);

        return this.modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User fetchedUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        return this.modelMapper.map(fetchedUser, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {

        List<User> userList = this.userRepo.findAll();

        return userList.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User fetchedUser = this.userRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User", "user_email" + email, 0));

        return this.modelMapper.map(fetchedUser, UserDto.class);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return null;
    }

    @Override
    public void deleteUserById(Integer userId) {

        User fetchedUser = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));

        this.userRepo.delete(fetchedUser);
    }

    @Override
    public List<UserDto> searchUserByFirstNameOrLastName(String firstName, String lastName) {

        if (firstName.equals("") && lastName.equals("")) return null;

        if (lastName.equals("")) {
            return findByFirstName(firstName);
        } else if (firstName.equals("")) {
            return findByLastName(lastName);
        } else {
            return searchByFirstNameOrLastName(firstName, lastName);
        }
    }

    private List<UserDto> searchByFirstNameOrLastName(String firstName, String lastName) {

        List<User> userList = this.userRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);

        return userList.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    private List<UserDto> findByFirstName(String firstName) {

        List<User> userList = this.userRepo.findByFirstNameStartingWithIgnoreCase(firstName);

        return userList.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    private List<UserDto> findByLastName(String lastName) {

        List<User> userList = this.userRepo.findByLastNameStartingWithIgnoreCase(lastName);

        return userList.stream().map(user -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
