package com.redrock.sdk.modules.generic;

import com.redrock.sdk.component.GenericModule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModuleMessage {

  public HashMap<Class, List<GenericModule>> lsMsgRegisters;

  public ModuleMessage() {
    lsMsgRegisters = new HashMap<>();
  }

  public <T> void register(GenericModule module, Class<T> type){
    if (lsMsgRegisters.containsKey(type)) {

      List<GenericModule> lsModules = lsMsgRegisters.get(type);
      if (!lsModules.contains(module))
        lsMsgRegisters.get(type).add(module);
      return;
    }

    List<GenericModule> ls = new ArrayList<>();
    ls.add(module);
    lsMsgRegisters.put(type, ls);

  }

  public void sendMsg(GenericMessage msg) {
    if (!lsMsgRegisters.containsKey(msg.getClass()))
      return;

    List<GenericModule> modules = lsMsgRegisters.get(msg.getClass());
    for (GenericModule module : modules)
      module.handleMsg(msg);
  }

}
