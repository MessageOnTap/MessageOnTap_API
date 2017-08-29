/*
  Copyright 2017 CHIMPS Lab, Carnegie Mellon University

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package edu.cmu.chimps.messageontap_api;

/**
 * Created by adamyi on 25/08/2017.
 */

public class Constraint {
    public enum Relation {UNKNOWN, CONCATENATION, DIRECT_SUBORDINATION, NESTED_SUBORDINATION}


    private String tagA_name = "";
    private String tagB_name = "";
    private Relation relation = Relation.UNKNOWN;

    public Constraint(String tagA_name, String tagB_name, Relation relation) {
        this.tagA_name = tagA_name;
        this.tagB_name = tagB_name;
        this.relation = relation;
    }

    public String getTagA() {
        return tagA_name;
    }

    public String getTagB() {
        return tagB_name;
    }

    public Relation getRelation() {
        return relation;
    }
}