/*******************************************************************************
 * Copyright (c) 2024 Robert Bosch Manufacturing Solutions GmbH and others
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Apache License, Version 2.0 which is available at
 * https://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 ******************************************************************************/

package org.eclipse.tractusx.semantics.registry.controller;

import org.eclipse.tractusx.semantics.aas.registry.api.SubmodelDescriptorApiDelegate;
import org.eclipse.tractusx.semantics.aas.registry.model.SubmodelEndpointAuthorization;
import org.eclipse.tractusx.semantics.registry.service.ShellService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubmodelDescriptorAuthorizationApiDelegate implements SubmodelDescriptorApiDelegate {

   private final ShellService shellService;

   public SubmodelDescriptorAuthorizationApiDelegate( ShellService shellService ) {
      this.shellService = shellService;
   }

   @Override
   public ResponseEntity<Void> postSubmodelDescriptorAuthorized(
         SubmodelEndpointAuthorization submodelEndpointAuthorization, String externalSubjectId ) {
      boolean visible = shellService.hasAccessToShellWithVisibleSubmodelEndpoint( submodelEndpointAuthorization.getSubmodelEndpointUrl(), externalSubjectId );
      if ( visible ) {
         return ResponseEntity.ok().build();
      } else {
         return ResponseEntity.status( HttpStatus.FORBIDDEN ).build();
      }
   }
}