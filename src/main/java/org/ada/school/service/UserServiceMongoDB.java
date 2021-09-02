package org.ada.school.service;

import org.ada.school.document.UserDocument;
import org.ada.school.dto.UserDto;
import org.ada.school.model.User;
import org.ada.school.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDB implements UserService{

    private final UserRepository userRepository;

       public UserServiceMongoDB(@Autowired UserRepository userRepository )
        {
            this.userRepository = userRepository;
        }

        @Override
        public User create( User user )
        {
            UserDocument userDocument = new UserDocument(user.getId(),user.getName(),user.getEmail(),user.getLastName(),user.getCreatedAt());
            userRepository.save(userDocument);
            return user;
        }

        @Override
        public User findById( String id ) throws Exception {
            UserDocument userDocument = new UserDocument();
            User user = new User();
            try {
                userDocument = userRepository.findById(id).orElseThrow(()->new Exception ("No se encontro un usuario con ese id"));
                user.setId(userDocument.getId());
                user.setName(userDocument.getName());
                user.setEmail(userDocument.getEmail());
                user.setLastName(userDocument.getLastName());
                user.setCreatedAt(userDocument.getCreatedAt());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return user;
        }

        @Override
        public List<User> all()
        {
            ArrayList<User> usersList= new ArrayList<>();
            List<UserDocument> usersDocumentsList= userRepository.findAll();
            for (int i=0; i<usersDocumentsList.size(); i++){
                User user = new User(usersDocumentsList.get(i).getId(),usersDocumentsList.get(i).getName(),usersDocumentsList.get(i).getEmail(),usersDocumentsList.get(i).getLastName(),usersDocumentsList.get(i).getCreatedAt());
                usersList.add(user);
            }


            return usersList;
        }

        @Override
        public boolean deleteById( String id )
        {

            if (userRepository.findById(id).isPresent()){
                userRepository.deleteById(id);
                return true;
            }else{
                return false;
            }

        }

        @Override
        public User update( UserDto userDto, String id )
        {
            if (userRepository.findById(id) != null){
                User user = new User(userDto);
                UserDocument userDocument = new UserDocument(user.getId(), user.getName(), user.getEmail(), user.getLastName(),user.getCreatedAt());
                userRepository.save(userDocument);
                return user;
            }
            return null;
        }
}
