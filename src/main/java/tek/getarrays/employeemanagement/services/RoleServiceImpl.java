package tek.getarrays.employeemanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tek.getarrays.employeemanagement.Exception.UserNotFoundException;
import tek.getarrays.employeemanagement.entity.Role;
import tek.getarrays.employeemanagement.entity.User;
import tek.getarrays.employeemanagement.repository.RoleRepo;
import tek.getarrays.employeemanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService{

    private final UserRepository userRepo;
    private final RoleRepo roleRepo;

    private User findUser(long id){return userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));}

    private Role findRole(long id){return roleRepo.findById(id).orElseThrow(() -> new UserNotFoundException("Role with ID " + id + " not found"));}


    @Override
    public void assignRoleToUser(long userId, long roleId) {
        User user = findUser(userId);
        Role role = findRole(roleId);
        user.getRoles().add(role);
        userRepo.save(user);
    }
}
