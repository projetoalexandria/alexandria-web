(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('AgeBracket', AgeBracket);

    AgeBracket.$inject = ['$resource'];

    function AgeBracket ($resource) {
        var resourceUrl =  'api/age-brackets/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
