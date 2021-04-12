/*
 * Copyright 2017 Judge Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zw.org.zvandiri.report.api.service;

import java.util.List;
import zw.org.zvandiri.business.util.dto.SearchDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientExitingProgramPivotDTO;
import zw.org.zvandiri.business.util.pivot.dto.BasePatientPivotDTO;

/**
 *
 * @author Judge Muzinda
 */
public interface PatientPivotService {
    
    public List<BasePatientPivotDTO> get(SearchDTO dto);
    
    public List<BasePatientExitingProgramPivotDTO> getInactivePatients(SearchDTO dto);
}