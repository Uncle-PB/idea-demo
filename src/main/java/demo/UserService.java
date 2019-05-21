package demo;

import org.springframework.stereotype.Service;
//0001
@Service
public class UserService {
    private String name;
    public String getName(){
        return name="yeah,my name is dra";
    }
}
