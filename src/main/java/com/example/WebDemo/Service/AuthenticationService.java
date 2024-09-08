//package com.example.WebDemo.Service;
//
//import com.example.WebDemo.Dto.AuthenticationRequest;
//import com.example.WebDemo.Dto.AuthenticationResponse;
//import com.example.WebDemo.Dto.IntrospectRequest;
//import com.example.WebDemo.Dto.IntrospectResponse;
//import com.example.WebDemo.Model.User;
//import com.example.WebDemo.Repository.UserRepository;
//import com.example.WebDemo.exception.AppException;
//import com.example.WebDemo.exception.ErrorCode;
//import com.nimbusds.jose.*;
//import com.nimbusds.jose.crypto.MACSigner;
//import com.nimbusds.jose.crypto.MACVerifier;
//import com.nimbusds.jwt.JWTClaimsSet;
//import com.nimbusds.jwt.SignedJWT;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.experimental.NonFinal;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.text.ParseException;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//import java.util.Date;
//import java.util.StringJoiner;
//
//@Service
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class AuthenticationService {
//    UserRepository userRepository ;
//
//    @NonFinal
//    protected static final String SIGNER_KEY = "GQl2Lx55TAdcmGisLHMLGKrlKt+7LjAxa42Tlxe7w7+bwwLDrd88MVu/NdNr7L8l";
//
//
//    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
//        var token = request.getToken();
//
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//        SignedJWT signedJWT = SignedJWT.parse(token);
//
//        Date expityTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//        var verified = signedJWT.verify(verifier);
//
//        return IntrospectResponse.builder()
//                .valid(verified && expityTime.after(new Date()))
//                .build();
//    }
//
//    public AuthenticationResponse authenticate(AuthenticationRequest request) {
//        var user = userRepository.findByUserName(request.getUsername())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        boolean authenticated = passwordEncoder.matches(request.getPassword(),user.getPassWord());
//
//        if(!authenticated){
//            throw new AppException(ErrorCode.UNAUTHENTICATED);
//        }
//
//        var token = generateToken(user);
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .authenticated(true)
//                .build();
//    }
//
//
//    private String generateToken(User user){
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(user.getUserName())
////                .issuer("hamom.com")
//                .issueTime(new Date())
//                .expirationTime(new Date(
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
//                ))
//                .claim("scope",buildScope(user))
//                .build();
//
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//
//        JWSObject jwsObject = new JWSObject(header , payload);
//
//
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            return jwsObject.serialize();
//        } catch (JOSEException e) {
////            log.error("Cannot create token")
//            throw new RuntimeException(e);
//        }
//    }
//
//
//
//
//    private String buildScope(User user) {
//        StringJoiner stringJoiner = new StringJoiner(" ");
//
//        if (!CollectionUtils.isEmpty(user.getRoles()))
//            user.getRoles().forEach(stringJoiner::add);
//
//        return stringJoiner.toString();
//    }
//
//
//}