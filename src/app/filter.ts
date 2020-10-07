export enum FilterType {
    Follower,
    Keyword
}

export interface Filter {
    name: string;
    type: FilterType;
}