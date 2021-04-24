package com.canhlabs.shorten.share;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ResultListInfo<T> extends ResultInfo<T> {
    private List<T> data;
}
