package org.account.cl.permissions.impl;

import org.account.cl.JcSecurityUtils;
import org.account.cl.User;
import org.account.cl.UserDao;
import org.account.cl.permissions.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${security.my.private-key}")
    private String privateKey;

    @Value("${security.my.public-key}")
    private String publicKey;

    @Autowired
    @Qualifier("userDaoCacheImpl")
    private UserDao userDaoCacheImpl;

    @Autowired
    @Qualifier("defaultPasswordEncoder")
    private PasswordEncoder passwordEncoder;

    {
        this.privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIsFC8TxOaiqiNwi+PFUWMU5YOqllzThHyQg2j27Viw0BNDCBUA2vVxTR/tgGjjluSCaqjMzHraA7ZtQinvtD0jR7wwqmIUqtnlgkDAi62pTF3AANnuLRmRGdXX/Qy8x7T+1S6zm4215R+i3oMwM5FNJkr+z0If1nVtuEsoxyGnjAgMBAAECgYBfHle8K7Tg07YKsR8VuDl40FagliZMNxAgnx3UCR7f5cO5tlbzZcDQr+bbdxjZ/0xYo5p6p6qHAtYQY94tBrlifTYn5HbmQacaVQ2eHLYi4IhrzomH6CerSYL6KJPM8uccwOsPmTjYbrYO1Q5ewmmNau20WGYBb2PGwe9OARPMMQJBANF+hnpBceUi/dboFZCARleJlpMrz/T8Nf/jKTNuZqKapq5WZRTVgGPg6G2KumwV4TjD3iqzixddILpSYwi+VTkCQQCp4Xsp2lzIwcEtC2gAhxsbX+x3RThIcA7P33W3MXMtROjOMDNotVFXy8HavcGmskctK4gjBwsHtAOQ0UYKNLP7AkAqCPc2xLbzwSep3XumOPbkTak28o2RnKHBLHVx6m8RpXJYfOFfYs+WOuSoRjPNyD4ew75qVwhLsdYFTM6uTHKRAkEAlmdKawCoWxmn1SCfILB9YFwp+GLxdLi5dHNsPhfq2C6FS1/DdDXr4aZFaEvOcq6uc6Bx9EkdC+HlskaGEy0cFwJAA8LXayt346misCrkG3ZORUEFHoTCwTdFmqArzBBDiHmiUXddZaDmQsMyX3yVaYBCltl/n3tu7H5Gffht242oqA==";
        this.publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCLBQvE8TmoqojcIvjxVFjFOWDqpZc04R8kINo9u1YsNATQwgVANr1cU0f7YBo45bkgmqozMx62gO2bUIp77Q9I0e8MKpiFKrZ5YJAwIutqUxdwADZ7i0ZkRnV1/0MvMe0/tUus5uNteUfot6DMDORTSZK/s9CH9Z1bbhLKMchp4wIDAQAB";
    }

    @Override
    public boolean addUser(User user) {
        // 将用户的密码进行解码
        String original = getDecodePassword(user.getPassword());
        // 将用户密码进行编码
        user.setPassword(getBCryptPassword(original));
        // 凭证未过期
        user.setCredentialsExpired(0);
        // 用户可用
        user.setIsEnable(1);
        // 用户未过期
        user.setExpired(0);
        // 用户未锁定
        user.setLocked(0);
        return userDaoCacheImpl.addUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDaoCacheImpl.getUserByUsername(username);
    }

    @Override
    public String getDecodePassword(String encodePassword) {
        try {
            // password 需要进行Base64解码
            byte[] pwd = Base64.getDecoder().decode(encodePassword);
            // 进行RSA校验
            return JcSecurityUtils.decryptByPrivate(new String(pwd), JcSecurityUtils.getPrivateKey(privateKey));
        } catch (Exception e) {
            logger.warn("密码解码失败: " + e.getMessage() + " 未按照规则上传密码!");
            return "";
        }
    }

    /**
     * 将密码变成数据库中存储的形式
     * @param password
     * @return
     */
    private String getBCryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * 将密码变成前端发送过来的形式 WARNING 测试用 正式无用
     * @param password
     * @return
     */
    private String getWebPassword(String password) {
        password = JcSecurityUtils.encryptByPublic(password.getBytes(), JcSecurityUtils.getPublicKey(publicKey));
        password = Base64.getEncoder().encodeToString(password.getBytes());
        return password;
    }

    public static void main(String[] args) {
        UserServiceImpl obj = new UserServiceImpl();
        String pwd = "123456";
        String publicPassword = obj.getWebPassword(pwd);
        System.out.println("前端加密后是: " + publicPassword);

        String pt = new String(Base64.getDecoder().decode(publicPassword));
        String password = JcSecurityUtils.decryptByPrivate(pt, JcSecurityUtils.getPrivateKey(obj.privateKey));
        System.out.println("原密码是: " + password);

        if (password.equals(pwd)) {
            String dbPassowrd = new BCryptPasswordEncoder().encode(password);
            System.out.println("保存在数据库中的是: " + dbPassowrd);
        }

        System.out.println(obj.getWebPassword("abc123"));
    }
}
