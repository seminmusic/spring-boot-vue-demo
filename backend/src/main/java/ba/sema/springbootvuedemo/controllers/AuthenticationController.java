package ba.sema.springbootvuedemo.controllers;

import ba.sema.springbootvuedemo.configurations.JwtUtils;
import ba.sema.springbootvuedemo.configurations.SecurityUserDetails;
import ba.sema.springbootvuedemo.models.authentication.LoginRequestModel;
import ba.sema.springbootvuedemo.models.authentication.LoginResponseModel;
import ba.sema.springbootvuedemo.models.authentication.RegisterUserRequestModel;
import ba.sema.springbootvuedemo.models.authentication.RegisterUserResponseModel;
import ba.sema.springbootvuedemo.services.SecurityUserRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController
{
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    //
    private final SecurityUserRoleService securityUserRoleService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, SecurityUserRoleService securityUserRoleService)
    {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.securityUserRoleService = securityUserRoleService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseModel> login(@Valid @RequestBody LoginRequestModel loginRequestModel)
    {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestModel.getUsername(), loginRequestModel.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        SecurityUserDetails securityUserDetails = (SecurityUserDetails) authentication.getPrincipal();
        List<String> roles = securityUserDetails.getAuthorities().stream().map(GrantedAuthority :: getAuthority).collect(Collectors.toList());

        logger.info(String.format("User %s logged in successfully.", loginRequestModel.getUsername()));
        LoginResponseModel loginResponseModel = new LoginResponseModel(jwt, securityUserDetails.getId(), securityUserDetails.getEmail(), securityUserDetails.getUsername(), roles);
        return ResponseEntity.ok(loginResponseModel);
    }

    @PostMapping("/register-user")
    @PreAuthorize("hasRole(T(ba.sema.springbootvuedemo.enums.RoleEnum).ADMIN)")
    public ResponseEntity<RegisterUserResponseModel> registerUser(@Valid @RequestBody RegisterUserRequestModel registerUserRequestModel)
    {
        if (securityUserRoleService.userExistsByUsername(registerUserRequestModel.getUsername()))
        {
            return ResponseEntity.badRequest().body(new RegisterUserResponseModel("Error: Username already exists."));
        }
        if (securityUserRoleService.userExistsByEmail(registerUserRequestModel.getEmail()))
        {
            return ResponseEntity.badRequest().body(new RegisterUserResponseModel("Error: Email is already in use."));
        }

        securityUserRoleService.createUser(
            registerUserRequestModel.getEmail(),
            registerUserRequestModel.getUsername(),
            passwordEncoder.encode(registerUserRequestModel.getPassword()),
            registerUserRequestModel.getRoles()
        );
        return ResponseEntity.ok(new RegisterUserResponseModel("User registered successfully."));
    }
}
