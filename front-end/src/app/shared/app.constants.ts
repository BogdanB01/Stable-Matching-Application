export const APP_CONSTANTS = {
    ENDPOINT: 'http://127.0.0.1:8080',

    STUDENT_MENU: [
        {
            'name' : 'MENU.PROJECTS',
            'icon' : 'dashboard',
            'link' : '/projects',
            'open' : false,
        },
        {
            'name' : 'MENU.STUDENT_ACCOUNT',
            'icon' : 'account_box',
            'link' : false,
            'open' : false,
            'sub' : [
                {
                    'name' : 'MENU.MY_ACCOUNT',
                    'icon' : 'account_box',
                    'link' : '/student-account',
                    'chip' : false,
                    'open' : true
                },
                {
                    'name' : 'MENU.ACCOUNT_SETTINGS',
                    'icon' : 'build',
                    'link' : '/student-settings',
                    'chip' : false,
                    'open' : true
                }
            ]
        },
        {
            'name' : 'MENU.LANGUAGE',
            'icon' : 'language',
            'link' : false,
            'open' : false,
            'sub' : [
                {
                    'name' : 'MENU.RO',
                    'icon' : 'outlined_flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                },
                {
                    'name' : 'MENU.EN',
                    'icon' : 'flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                }
            ]
        },
        {
            'name' : 'MENU.LOGOUT',
            'icon' : 'exit_to_app',
            'link' : true,
            'open' : false
        }
    ],

    ADMIN_MENU: [
        {
            'name' : 'MENU.PROJECTS',
            'icon' : 'dashboard',
            'link' : '/projects',
            'open' : false,
        },
        {
            'name' : 'MENU.ADMIN_ACCOUNT',
            'icon' : 'account_box',
            'link' : false,
            'open' : false,
            'sub' : [
                {
                    'name' : 'MENU.USERS',
                    'icon' : 'face',
                    'link' : '/admin-account',
                    'chip' : false,
                    'open' : true
                },
                {
                    'name' : 'MENU.MATCHING_AREA',
                    'icon' : 'build',
                    'link' : '/matching-area',
                    'chip' : false,
                    'open' : true
                }
            ]
        },
        {
            'name' : 'MENU.LANGUAGE',
            'icon' : 'language',
            'link' : false,
            'open' : false,
            'sub' : [
                {
                    'name' : 'MENU.RO',
                    'icon' : 'outlined_flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                },
                {
                    'name' : 'MENU.EN',
                    'icon' : 'flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                }
            ]
        },
        {
            'name' : 'MENU.LOGOUT',
            'icon' : 'exit_to_app',
            'link' : true,
            'open' : false
        }
    ],

    LECTURER_MENU: [
        {
            'name' : 'MENU.PROJECTS',
            'icon' : 'dashboard',
            'link' : '/projects',
            'open' : false,
        },
        {
            'name' : 'MENU.LECTURER_ACCOUNT',
            'icon' : 'account_circle',
            'link' : '/lecturer-account',
            'open' : true
        },
        {
            'name' : 'MENU.LANGUAGE',
            'icon' : 'language',
            'link' : false,
            'open' : false,
            'sub' : [
                {
                    'name' : 'MENU.RO',
                    'icon' : 'outlined_flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                },
                {
                    'name' : 'MENU.EN',
                    'icon' : 'flag',
                    'link' : '',
                    'chip' : false,
                    'open' : true
                }
            ]
        },
        {
            'name' : 'MENU.LOGOUT',
            'icon' : 'exit_to_app',
            'link' : true,
            'open' : false
        }
    ],

    ROLE_LECTURER: 'ROLE_LECTURER',
    ROLE_STUDENT: 'ROLE_STUDENT',
    ROLE_ADMIN: 'ROLE_ADMIN'
};
