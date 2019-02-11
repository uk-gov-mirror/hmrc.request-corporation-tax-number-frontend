/*
 * Copyright 2019 HM Revenue & Customs
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

package views

import controllers.routes
import views.behaviours.ViewBehaviours
import views.html.index

class IndexViewSpec extends ViewBehaviours {

  def view = () => index(frontendAppConfig, call)(fakeRequest, messages)

  val call = routes.CheckYourAnswersController.onPageLoad()

  "Index view" must {

    behave like normalPage(view, "index")
  }

  "link should direct the user to check-your-answers page" in {
    val doc = asDocument(view())
    doc.getElementById("start-now").attr("href") must include("/check-your-answers")
  }

  "Page should contain all to use this form content" in {
    val doc = asDocument(view())
    assertContainsText(doc, messagesApi("index.guidance"))
    assertContainsText(doc, messagesApi("index.useForm.title"))
    assertContainsText(doc, messagesApi("index.useForm.item1"))
    assertContainsText(doc, messagesApi("index.useForm.item2"))
    assertContainsText(doc, messagesApi("index.useForm.item3"))
  }

  "Page should contain before you start content" in {
    val doc = asDocument(view())
    assertContainsText(doc, messagesApi("index.beforeYouStart.title"))
    assertContainsText(doc, messagesApi("index.beforeYouStart.item1"))
    assertContainsText(doc, messagesApi("index.beforeYouStart.item2"))
  }
}
