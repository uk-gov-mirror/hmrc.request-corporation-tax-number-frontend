/*
 * Copyright 2021 HM Revenue & Customs
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

package controllers

import models.NormalMode
import play.api.mvc.{Call, MessagesControllerComponents, Request}
import play.api.test.Helpers._
import views.html.IndexView

class IndexControllerSpec extends ControllerSpecBase {

  implicit val cc: MessagesControllerComponents = injector.instanceOf[MessagesControllerComponents]
  val view = app.injector.instanceOf[IndexView]

  def call(implicit request: Request[_]) : Call = routes.CompanyDetailsController.onPageLoad(NormalMode)

  "Index Controller" must {
    "return 200 for a GET" in {
      val result = new IndexController(frontendAppConfig, cc, view, messagesApi).onPageLoad()(fakeRequest)
      status(result) mustBe OK
    }

    "return the correct view for a GET" in {
      val result = new IndexController(frontendAppConfig, cc, view, messagesApi).onPageLoad()(fakeRequest)
      contentAsString(result) mustBe view(call(fakeRequest))(fakeRequest, messages).toString
    }
  }
}
