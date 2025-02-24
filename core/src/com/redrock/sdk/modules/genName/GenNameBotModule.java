package com.redrock.sdk.modules.genName;

import com.redrock.Main;
import com.redrock.sdk.component.GenericModule;
import com.redrock.sdk.modules.generic.GenericMessage;

public class GenNameBotModule extends GenericModule {

  private final String[] arrNames = new String[] {
    "Oliver", "Jack", "Harry", "Jacob", "Charlie", "Thomas", "George", "Oscar", "James", "William", "Jake", "Connor",
    "Callum", "Jacob", "Kyle", "Joe", "Reece", "Rhys", "Charlie", "Damian", "Noah", "Liam", "Mason", "Jacob", "Ethan",
    "Michael", "Daniel", "Robert", "David", "Richard", "Joseph", "Charles", "Thomas"
  };

  public GenNameBotModule() {
//    Main.moduleMessage().register(this, GenNameMsg.GEN_NAME_MSG);
  }

  private void handleGenName(GenNameMsg msg) {

    for (int i = 0; i < msg.maxSizeName; i++) {
      boolean isExist = true;

      while (isExist) {
        int     rndIdxName  = (int) (Math.random() * arrNames.length);
        String  name        = arrNames[rndIdxName];

        if (!msg.lsNames.contains(name)) {
          msg.lsNames.add(name);
          isExist = false;
        }
      }
    }
  }
}
