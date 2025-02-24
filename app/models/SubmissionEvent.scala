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

package models

import javax.inject.Inject

import controllers.routes
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.play.audit.AuditExtensions._
import uk.gov.hmrc.play.audit.model.DataEvent

class SubmissionEvent @Inject()(data: Map[String, String])(implicit hc: HeaderCarrier)
  extends DataEvent(
    auditSource = "request-corporation-tax-number",
    auditType = "submission",
    detail = hc.toAuditDetails(data.toSeq :_*),
    tags = hc.toAuditTags(
      "Submission from Request Corporation Tax number Frontend",
      routes.CheckYourAnswersController.onPageLoad().url))
