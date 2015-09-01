package org.fri.helloworld.beans;

import org.fri.helloworld.models.IOC;

import javax.ejb.Remote;
import java.util.List;


@Remote
public interface IocInterface {
    public void addIOC(IOC ioc);
    public void removeIOC(Integer id);
    public List<IOC> showIOCs();
}
