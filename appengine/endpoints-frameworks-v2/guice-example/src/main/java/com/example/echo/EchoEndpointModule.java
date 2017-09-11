/*
 * Copyright (c) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not  use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.example.echo;

import com.google.api.control.ServiceManagementConfigFilter;
import com.google.api.control.extensions.appengine.GoogleAppEngineControlFilter;
import com.google.api.server.spi.EndpointsServlet;
import com.google.api.server.spi.guice.EndpointsModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.servlet.GuiceFilter;
import java.util.HashMap;
import java.util.Map;

// [START endpoints_module]
public class EchoEndpointModule extends EndpointsModule {
  @Override
  public void configureServlets() {
    filter("/_ah/api/*").through(GuiceFilter.class);

    Map<String, String> apiController = new HashMap<String, String>();
    apiController.put("endpoints.projectId", "YOUR_PROJECT_ID");
    apiController.put("endpoints.serviceName", "YOUR_PROJECT_ID.appspot.com");

    filter("/_ah/api/*").through(ServiceManagementConfigFilter.class);
    filter("/_ah/api/*").through(GoogleAppEngineControlFilter.class, apiController);

    serve("/_ah/api/*").with(EndpointsServlet.class);

    bind(Echo.class).toInstance(new Echo());
    configureEndpoints("/_ah/api/*", ImmutableList.of(Echo.class));
    super.configureServlets();
  }
}
// [END endpoints_module]
