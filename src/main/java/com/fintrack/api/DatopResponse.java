package com.fintrack.api;

import java.util.List;

public record DatopResponse(
        String rspCode,
        String rspMessage,
        List<DatopTransaction> data
) {
}
