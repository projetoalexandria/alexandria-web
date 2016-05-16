'use strict';

describe('Controller Tests', function() {

    describe('School Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockSchool, MockCep, MockUser;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockSchool = jasmine.createSpy('MockSchool');
            MockCep = jasmine.createSpy('MockCep');
            MockUser = jasmine.createSpy('MockUser');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'School': MockSchool,
                'Cep': MockCep,
                'User': MockUser
            };
            createController = function() {
                $injector.get('$controller')("SchoolDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:schoolUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
