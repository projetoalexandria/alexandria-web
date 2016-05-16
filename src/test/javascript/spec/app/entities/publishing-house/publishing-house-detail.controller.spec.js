'use strict';

describe('Controller Tests', function() {

    describe('PublishingHouse Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPublishingHouse, MockCep;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPublishingHouse = jasmine.createSpy('MockPublishingHouse');
            MockCep = jasmine.createSpy('MockCep');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'PublishingHouse': MockPublishingHouse,
                'Cep': MockCep
            };
            createController = function() {
                $injector.get('$controller')("PublishingHouseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:publishingHouseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
