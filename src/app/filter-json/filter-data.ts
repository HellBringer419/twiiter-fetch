import { Filter } from './filter';
import { FilterMetaDataSent } from './filter-meta-data-sent';

export interface FilterData {
    data: Filter[];
    meta: FilterMetaDataSent;
}