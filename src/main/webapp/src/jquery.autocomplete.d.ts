interface JQueryAutocompleteOptions {
    serviceUrl?: string;
    lookup?: AutocompleteSuggestion[];
    minChars: number;
    maxHeight: number;
    deferRequestBy?: number;
    width?: number;
    params?: Object;
    delimiter?: any;
    zIndex?: number;
    type?: string;
    noCache?: boolean;
    tabDisabled?: boolean;
    paramName?: string;
    autoSelectFirst?: boolean;
    appendTo: any;
    dataType: string;

    lookupFilter?(suggestion: AutocompleteSuggestion, query: string, queryLowercase: string): any;

    onSelect?(suggestion: AutocompleteSuggestion): void;

    formatResult?(suggestion: AutocompleteSuggestion, currentValue: string): string;

    onSearchStart?(query: string): void;

    onSearchComplete?(query: string): void;

    transformResult?(response: any, originalQuery: string): AutocompleteSuggestion[];
}

interface AutocompleteSuggestion {
    value: string;
    data: any;
}

interface AutocompleteInstance {
    setOptions(options: JQueryAutocompleteOptions): void;

    clear(): void;

    clearCache(): void;

    disable(): void;

    enable(): void;

    hide(): void;

    dispose(): void;
}
