package com.pasviegas.discounts.api.v1
import com.pasviegas.discounts.api.core.streams.BidirectionalStream
import com.pasviegas.discounts.api.v1.methods.GetDiscounts
import com.pasviegas.discounts.database.Repository
import com.pasviegas.discounts.rules.RulesRegistry
import com.pasviegas.discounts.v1.api.v1.{ApiGrpc, GetDiscountsRequest, GetDiscountsResponse}
import io.grpc.ServerServiceDefinition
import io.grpc.stub.StreamObserver

import scala.concurrent.ExecutionContextExecutor

class ApiV1Service(repository: Repository, rules: RulesRegistry[_]) extends ApiGrpc.Api {

  override def getDiscounts(response: StreamObserver[GetDiscountsResponse]): StreamObserver[GetDiscountsRequest] =
    BidirectionalStream(response, GetDiscounts(repository, rules))

}

object ApiV1Service {

  /**
    *
    * @param repository
    * @param rules
    * @param executor
    * @return
    */
  def apply(repository: Repository,
            rules: RulesRegistry[_],
            executor: ExecutionContextExecutor): ServerServiceDefinition =
    ApiGrpc.bindService(new ApiV1Service(repository, rules), executor)
}
