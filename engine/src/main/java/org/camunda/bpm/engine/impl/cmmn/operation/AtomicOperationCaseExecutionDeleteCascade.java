/*
 * Copyright © 2012 - 2018 camunda services GmbH and various authors (info@camunda.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.camunda.bpm.engine.impl.cmmn.operation;

import java.util.List;

import org.camunda.bpm.engine.impl.cmmn.execution.CmmnExecution;

/**
 * @author Roman Smirnov
 *
 */
public class AtomicOperationCaseExecutionDeleteCascade implements CmmnAtomicOperation {

  public String getCanonicalName() {
    return "delete-cascade";
  }

 protected CmmnExecution findFirstLeaf(CmmnExecution execution) {
   List<? extends CmmnExecution> executions = execution.getCaseExecutions();

   if (executions.size() > 0) {
     return findFirstLeaf(executions.get(0));
   }
   return execution;
 }

  public void execute(CmmnExecution execution) {
    CmmnExecution firstLeaf = findFirstLeaf(execution);

    firstLeaf.remove();

    CmmnExecution parent = firstLeaf.getParent();
    if (parent != null) {
      parent.deleteCascade();
    }
  }

  public boolean isAsync(CmmnExecution execution) {
    return false;
  }

}
