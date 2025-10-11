package com.meta.emogi.feature.base.domain

import java.io.IOException

/**
 * API 통신 결과 재시도가 필요할 때 던지는 예외
 */
class RetryNeededException(message: String = "재시도가 필요합니다.") : IOException(message)
